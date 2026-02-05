# Wycinacz - Optymalizacja Cięcia Kształtowników Stalowych

Aplikacja webowa do optymalizacji cięcia kształtowników stalowych metodą First Fit Decreasing.

## 🚀 Funkcjonalności

- ✂️ **Optymalizacja cięcia** - algorytm FFD minimalizuje ilość sztang i odpadów
- 📊 **Wizualizacja wyników** - przejrzyste karty z informacjami o każdym cięciu
- 📋 **Kopiowanie do schowka** - szybki eksport wyników
- 📄 **Eksport do PDF** - profesjonalne dokumenty do druku
- 💾 **Eksport do TXT** - proste pliki tekstowe
- 🎨 **Nowoczesny interfejs** - responsywny design
- ⚡ **Szybkie obliczenia** - natychmiastowe rezultaty

## 🛠️ Technologie

- **Backend**: Node.js 18+, Express
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **PDF**: PDFKit
- **Package Manager**: npm

## 📦 Wymagania

- Node.js 18.0.0 lub nowszy
- npm 9.0.0 lub nowszy

## 🏃 Uruchomienie lokalne

### Instalacja zależności

```bash
npm install
```

### Uruchomienie serwera

```bash
npm start
```

### Tryb deweloperski (auto-restart)

```bash
npm run dev
```

Aplikacja dostępna pod: **http://localhost:3000**

## ☁️ Wdrożenie na Render

### Krok 1: Przygotowanie repozytorium

```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/twoj-username/wycinacz.git
git push -u origin main
```

### Krok 2: Konfiguracja Render

1. Zaloguj się na https://render.com
2. Kliknij **"New +"** → **"Web Service"**
3. Połącz z GitHub i wybierz repozytorium
4. Konfiguracja:
   - **Name**: wycinacz
   - **Environment**: Node
   - **Build Command**: `npm install`
   - **Start Command**: `npm start`
5. Kliknij **"Create Web Service"**

### Krok 3: Gotowe! 🎉

Aplikacja będzie dostępna pod adresem: `https://wycinacz.onrender.com`

## 📖 Użycie

1. **Wprowadź dane**
   - Wpisz długości elementów do cięcia (oddzielone przecinkami)
   - Opcjonalnie zmień dostępne długości sztang

2. **Oblicz**
   - Kliknij "Oblicz"
   - Wyniki pojawią się natychmiast

3. **Eksportuj**
   - Kopiuj do schowka
   - Eksportuj do PDF
   - Zapisz jako TXT

## 🧮 Algorytm

Aplikacja używa algorytmu **First Fit Decreasing (FFD)**:

1. Sortuje elementy od największego do najmniejszego
2. Dla każdego elementu próbuje umieścić go w istniejącej sztandze
3. Jeśli się nie da, wybiera najmniejszą nową sztangę
4. Minimalizuje liczbę sztang i odpady

### Przykład

**Dane wejściowe:**
- Elementy: 4500, 3000, 2500, 7000 mm
- Sztangi: 6000, 12100, 15100 mm

**Wynik:**
- Sztanga #1 (12100 mm): 7000, 4500 → strata 600 mm
- Sztanga #2 (6000 mm): 3000, 2500 → strata 500 mm
- **Łączna strata**: 1100 mm (9.1%)

## 📁 Struktura projektu

```
wycinacz-nodejs/
├── server.js              # Serwer Express + API
├── public/
│   └── index.html        # Frontend
├── package.json          # Zależności npm
├── .gitignore           # Pliki ignorowane przez Git
└── README.md            # Dokumentacja
```

## 🔌 API Endpoints

### POST `/api/optimize`
Optymalizuje plan cięcia.

**Request:**
```json
{
  "requiredCuts": [4500, 3000, 2500, 7000],
  "stockLengths": [6000, 12100, 15100]
}
```

**Response:**
```json
{
  "plans": [
    {
      "stockLength": 12100,
      "cuts": [7000, 4500],
      "waste": 600
    }
  ],
  "totalStockUsed": 2,
  "totalLength": 18100,
  "totalWaste": 1100,
  "summary": "Użyto sztang: 2 | Łączna długość: 18100 mm | Całkowita strata: 1100 mm"
}
```

### POST `/api/export/pdf`
Generuje PDF z wynikami.

### POST `/api/export/txt`
Generuje plik tekstowy.

### GET `/health`
Health check endpoint.

## 🤝 Contributing

Pull requesty są mile widziane!

## 📝 Licencja

MIT License

## 👨‍💻 Autor

Stworzone dla optymalizacji procesów produkcyjnych.
