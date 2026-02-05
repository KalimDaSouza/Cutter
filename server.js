const express = require('express');
const cors = require('cors');
const PDFDocument = require('pdfkit');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.static('public'));

// Algorytm optymalizacji - First Fit Decreasing
function optimizeCuts(requiredCuts, stockLengths) {
    // Sortuj malejąco
    const sortedCuts = [...requiredCuts].sort((a, b) => b - a);
    const result = [];
    
    for (const cut of sortedCuts) {
        let placed = false;
        
        // Spróbuj umieścić w istniejącej sztandze
        for (const plan of result) {
            const remaining = plan.stockLength - plan.cuts.reduce((sum, c) => sum + c, 0);
            if (remaining >= cut) {
                plan.cuts.push(cut);
                placed = true;
                break;
            }
        }
        
        // Jeśli nie udało się, użyj nowej sztangi
        if (!placed) {
            const suitableStock = stockLengths
                .filter(s => s >= cut)
                .sort((a, b) => a - b)[0];
            
            if (suitableStock) {
                result.push({
                    stockLength: suitableStock,
                    cuts: [cut]
                });
            } else {
                throw new Error(`Brak kształtownika o długości >= ${cut} mm`);
            }
        }
    }
    
    // Dodaj obliczenia waste
    return result.map(plan => ({
        ...plan,
        waste: plan.stockLength - plan.cuts.reduce((sum, c) => sum + c, 0)
    }));
}

// Oblicz podsumowanie
function calculateSummary(plans) {
    const totalStockUsed = plans.length;
    const totalLength = plans.reduce((sum, p) => sum + p.stockLength, 0);
    const totalWaste = plans.reduce((sum, p) => sum + p.waste, 0);
    
    return {
        totalStockUsed,
        totalLength,
        totalWaste,
        summary: `Użyto sztang: ${totalStockUsed} | Łączna długość: ${totalLength} mm | Całkowita strata: ${totalWaste} mm`
    };
}

// API Endpoints

// POST /api/optimize - Optymalizacja cięcia
app.post('/api/optimize', (req, res) => {
    try {
        const { requiredCuts, stockLengths } = req.body;
        
        if (!requiredCuts || !Array.isArray(requiredCuts) || requiredCuts.length === 0) {
            return res.status(400).json({ error: 'Brak wymaganych długości cięć' });
        }
        
        if (!stockLengths || !Array.isArray(stockLengths) || stockLengths.length === 0) {
            return res.status(400).json({ error: 'Brak dostępnych długości sztang' });
        }
        
        const plans = optimizeCuts(requiredCuts, stockLengths);
        const summary = calculateSummary(plans);
        
        res.json({
            plans,
            ...summary
        });
        
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});

// POST /api/export/pdf - Eksport do PDF
app.post('/api/export/pdf', (req, res) => {
    try {
        const { plans, summary } = req.body;
        
        const doc = new PDFDocument();
        
        // Ustaw nagłówki
        res.setHeader('Content-Type', 'application/pdf');
        res.setHeader('Content-Disposition', 'attachment; filename=wycinacz_wyniki.pdf');
        
        // Pipe PDF do response
        doc.pipe(res);
        
        // Dodaj zawartość
        doc.fontSize(18).text('Wyniki optymalizacji cięcia', { underline: true });
        doc.moveDown();
        
        plans.forEach((plan, index) => {
            doc.fontSize(12).text(
                `Sztanga #${index + 1} (${plan.stockLength} mm): ${plan.cuts.join(', ')} (strata: ${plan.waste} mm)`
            );
        });
        
        doc.moveDown();
        doc.fontSize(14).text(summary, { bold: true });
        
        // Zakończ dokument
        doc.end();
        
    } catch (error) {
        res.status(500).json({ error: 'Błąd generowania PDF' });
    }
});

// POST /api/export/txt - Eksport do TXT
app.post('/api/export/txt', (req, res) => {
    try {
        const { plans, summary } = req.body;
        
        let text = 'Wyniki optymalizacji cięcia\n';
        text += '============================\n\n';
        
        plans.forEach((plan, index) => {
            text += `Sztanga #${index + 1} (${plan.stockLength} mm): ${plan.cuts.join(', ')} (strata: ${plan.waste} mm)\n`;
        });
        
        text += `\n${summary}\n`;
        
        res.setHeader('Content-Type', 'text/plain; charset=utf-8');
        res.setHeader('Content-Disposition', 'attachment; filename=wycinacz_wyniki.txt');
        res.send(text);
        
    } catch (error) {
        res.status(500).json({ error: 'Błąd generowania TXT' });
    }
});

// GET / - Strona główna
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Health check endpoint
app.get('/health', (req, res) => {
    res.json({ status: 'ok' });
});

// Start serwera
app.listen(PORT, () => {
    console.log(`🔧 Wycinacz uruchomiony na porcie ${PORT}`);
    console.log(`📍 http://localhost:${PORT}`);
});
