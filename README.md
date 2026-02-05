# Wycinacz - Optymalizacja Cięcia Kształtowników Stalowych

Aplikacja webowa do optymalizacji cięcia kształtowników stalowych metodą First Fit Decreasing.

## 🚀 Funkcjonalności

- ✂️ **Optymalizacja cięcia** - algorytm FFD minimalizuje ilość sztang i odpadów
- 📊 **Wizualizacja wyników** - przejrzyste karty z informacjami o każdym cięciu
- 📋 **Kopiowanie do schowka** - szybki eksport wyników
- 📄 **Eksport do PDF** - profesjonalne dokumenty do druku
- 💾 **Eksport do TXT** - proste pliki tekstowe
- 🎨 **Nowoczesny interfejs** - responsywny design dostosowany do urządzeń mobilnych
- ⚡ **Szybkie obliczenia** - natychmiastowe rezultaty

## 🛠️ Technologie

- **Backend**: Spring Boot 3.2, Java 17
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **PDF**: iText 7
- **Build**: Maven

## 📦 Wymagania

- Java 17 lub nowszy
- Maven 3.6+

## 🏃 Uruchomienie lokalne

### Metoda 1: Maven

```bash
mvn spring-boot:run
```

### Metoda 2: JAR

```bash
mvn clean package
java -jar target/wycinacz-1.0.0.jar
```

Aplikacja dostępna pod: **http://localhost:8080**

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
3. Połącz z GitHub i wybierz repozytorium `wycinacz`
4. Render automatycznie wykryje konfigurację z `render.yaml`
5. Kliknij **"Create Web Service"**

### Krok 3: Gotowe! 🎉

Aplikacja będzie dostępna pod adresem: `https://wycinacz.onrender.com`

## 📖 Użycie

1. **Wprowadź dane**
   - Wpisz długości elementów do cięcia (oddzielone przecinkami)
   - Opcjonalnie zmień dostępne długości sztang magazynowych

2. **Oblicz**
   - Kliknij przycisk "Oblicz"
   - Poczekaj na wyniki (zwykle < 1 sekundy)

3. **Eksportuj**
   - Kopiuj wyniki do schowka
   - Eksportuj do PDF
   - Zapisz jako plik TXT

## 🧮 Algorytm

Aplikacja używa algorytmu **First Fit Decreasing (FFD)**:

1. Sortuje wszystkie elementy od największego do najmniejszego
2. Dla każdego elementu:
   - Próbuje umieścić w istniejącej sztandze z wystarczającą przestrzenią
   - Jeśli się nie da, wybiera najmniejszą nową sztangę, która pomieści element
3. Minimalizuje:
   - Liczbę użytych sztang
   - Całkowite odpady materiału

### Przykład

**Dane wejściowe:**
- Elementy do cięcia: 4500, 3000, 2500, 7000 mm
- Dostępne sztangi: 6000, 12100, 15100 mm

**Wynik:**
- Sztanga #1 (12100 mm): 7000, 4500 mm → strata: 600 mm
- Sztanga #2 (6000 mm): 3000, 2500 mm → strata: 500 mm
- **Łączna strata**: 1100 mm (9.1%)

## 📁 Struktura projektu

```
wycinacz/
├── src/
│   ├── main/
│   │   ├── java/com/wycinacz/
│   │   │   ├── WycinaczApplication.java       # Klasa główna
│   │   │   ├── controller/
│   │   │   │   ├── CuttingController.java     # REST API
│   │   │   │   └── HomeController.java        # Routing
│   │   │   ├── model/
│   │   │   │   ├── CutPlan.java              # Model planu cięcia
│   │   │   │   ├── OptimizationRequest.java  # DTO request
│   │   │   │   └── OptimizationResult.java   # DTO response
│   │   │   └── service/
│   │   │       ├── CuttingOptimizer.java     # Algorytm FFD
│   │   │       └── ExportService.java        # PDF/TXT export
│   │   └── resources/
│   │       ├── application.properties         # Konfiguracja
│   │       └── templates/
│   │           └── index.html                # Frontend
├── pom.xml                                    # Maven dependencies
├── render.yaml                                # Render config
├── .gitignore
└── README.md
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
    },
    {
      "stockLength": 6000,
      "cuts": [3000, 2500],
      "waste": 500
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
Generuje plik tekstowy z wynikami.

## 🤝 Contributing

Zgłoszenia błędów i pull requesty są mile widziane!

1. Fork projektu
2. Stwórz branch (`git checkout -b feature/NowaFunkcja`)
3. Commit zmian (`git commit -m 'Dodaj nową funkcję'`)
4. Push do brancha (`git push origin feature/NowaFunkcja`)
5. Otwórz Pull Request

## 📝 Licencja

MIT License - możesz swobodnie używać, modyfikować i dystrybuować.

## 👨‍💻 Autor

Stworzone z ❤️ dla optymalizacji procesów produkcyjnych.

## 🐛 Znane problemy

Brak obecnie znanych problemów. Jeśli znajdziesz jakiś, zgłoś go w Issues!

## 📮 Kontakt

Pytania? Sugestie? Otwórz Issue na GitHubie!
