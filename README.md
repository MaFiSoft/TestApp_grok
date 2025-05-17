# TestApp

Eine einfache Android-App, die ein Hauptmenü und zwei Untermenüs (Artikel, Geschäfte) mit einem anpassbaren Farbschema bietet.

## Voraussetzungen
- Codemagic.io (zum Erstellen der APK)
- GitHub-Repository (zum Hochladen dieses Projekts)

## Schritte zum Erstellen der APK mit Codemagic
1. Lade dieses Repository auf GitHub hoch.
2. Melde dich bei [Codemagic.io](https://codemagic.io) an.
3. Verbinde dein GitHub-Repository mit Codemagic.
4. Stelle sicher, dass die `codemagic.yaml`-Datei im Root-Verzeichnis liegt.
5. Starte den Build in Codemagic. Nach Abschluss kannst du die `app-debug.apk` unter "Artifacts" herunterladen.

## Funktionen
- Hauptmenü mit "Test-App"-Text und Navigation zu Untermenüs.
- Untermenüs (Artikel, Geschäfte) mit "Zurück"-Button.
- Optionsmenü im Hauptmenü mit Farbschema-Auswahl (Blau, Rot, Grün, Gelb, Lila).
- Dynamische Farbänderung der Kopf- und Fußzeile.

## Hinweise
- Die APK ist eine Debug-Version (nicht signiert). Für eine signierte Release-APK musst du einen Keystore in Codemagic konfigurieren.
- Min SDK: 24, Target SDK: 34.
