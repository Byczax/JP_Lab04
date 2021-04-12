# Laboratorium 4, TN 11:00

Podczas laboratorium należy zbudować aplikację o przyjaznym, graficznym interfejsie użytkownika. Interfejs ten powstać ma w oparciu o klasy SWING (zalecana biblioteka). Chętni mogą spróbować posłużyć się klasami JavaFX (to wykracza poza zakres kursu, poza tym mogą wystąpić problemy z dostępem do JavaFX, bo to osobny runtime wymagający osobnej instalacji, a później, odpowiedniego sparametryzowania wywołania wirtualnej maszyny: --module-path "\path to javafx\lib" --add-modules javafx.controls,javafx.fxml).
Budowana aplikacja ma pełnić rolę narzędzia umożliwiającego podnoszenie jakości świadczonych usług przez sieć handlowo/usługową poprzez umożliwienie ich opiniowania. Poprzez graficzny interfejs aplikacji powinno się dać zdefiniować ankietę, zebrać odpowiedzi, zwizualizować wyniki.
Zakładamy, że z aplikacji będą korzystać dwa typu użytkowników: menadżer oraz klient.
Menadżer: zarządza lista usług, definiuje ankiety, przypisuje ankiety do usług.
Klient: ocenia usługę wybierając ją najpierw z listy i wypełniając dynamicznie wygenerowany formularza ankiety.
Obaj użytkownicy mogą przeglądać wyniki ankiet.
Zarządzanie lista usług polegać ma na dodawaniu, usuwaniu, korekcie usług reprezentowanych przez nazwę (do reprezentacji usługi wystarczy tylko jeden atrybut przechowujący jakąś nazwę, np. "Kurier, oddział Wrocław"). Można to zrobić na osobnym panelu.
Definiowanie ankiety polegać ma na określaniu nazw i typów pól, jakie mają się na niej znaleźć. Definicja taka może przyjąć postać listy par: nazwa pola, typ pola. Dla uproszczenia proszę przyjąć, że do dyspozycji są tylko dwa typy pól: Integer (dopuszczalne wartości 0-10), String. Przykładowo definicja ankiety może przyjąć postać:

```txt
{{Czas dostawy, Integer}, {Jakość do ceny, Integer}, {Komentarz, String}}.
```

Implementacja samego definiowania ankiety może polegać na stworzeniu panelu, na którym menadżer będzie mógł wybierać typ pola z rozwijalnej listy (combobox) oraz wpisać w pole tekstowe nazwę pola. Następnie tak zdefiniowana para może trafić do listy po kliku na przycisku "Dodaj". Każda definicja typu ankiety powinna być dodatkowo nazwana, by dało się później listować definicje ankiet podczas ich przypisywania do listy usług (np. "Ocena dostawcy", "Ocena konsultanta").
Przypisywanie ankiet do usług polegać ma na podpięciu do usług definicji ankiety. Można to zrobić na osobnym panelu, choć lepszym pomysłem wydaje się być rozszerzenie możliwości panelu do zarządzania listą usług (przy okazji zarządzania listą usług można pod usługi podpinać definicje ankiet).
Implementacja oceniania usług polegać ma na umożliwieniu wybrania usługi z listy usług (dla których zdefiniowano ankiety) oraz na wyświetleniu wygenerowanego dynamicznie formularza zgodnie z definicją typu ankiety i umożliwieniu jego wypełnienia. Można wykorzystać do tego panel z odpowiednio dobranym menadżerem ułożenia.
Z każdą oceną danej usługi powinna być skojarzona informacja, kto tę ocenę wystawił (czyli jakiś identyfikator klienta) oraz kiedy to zrobił (czyli data zapisania ankiety).
Zakładamy, że podczas przeglądania wyników ankiet dla danej usługi będzie można wskazać pole (typu Integer), dla którego powinien wygenerować się histogram (wykres słupkowy pokazujący ilu klientów oceniając daną usługę wpisało 0, 1, ... 10 w tym właśnie polu).
