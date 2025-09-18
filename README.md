## Pour les PDF:
### J'ai téléchargé 3 fichiers .jar  
1. ``commons-logging-1.2.jar``
2. ``fontbox-2.0.27.jar``
3. ``pdfbox-2.0.27.jar``
### J'ai télécharger 1 font .ttf -> ``arial.ttf``

## Jeu de données pour le test
### Si dans `Sparadrap/serializedData` vous n'avez pas le fichier `data.ser`:<br>
1. Enlever le commentaire de la méthode Main.developpementDataInput pour charger les données et lancer le programme.<br>
2. Attention à bien remettre le commentaire pour désactiver la méthode aprés la première fois (ou les données seront toute
ajouter une nouvelle fois.)

### En cas de problème avec la sérialisation:
- Supprimer Sparadrap/serializedData/data.ser.
- Utiliser le jeu de données sans sérialisation: à la fin de la méthode developpmentDataInput() supprimer 
la ligne `DataSave.serialization();` avant le catch.
- Supprimer l'appel de la méthode ``DataSave.deserialization();``.

## Tests Unitaires
Il faudra supprimer le fichier ``data.ser`` après les tests unitaires et recharger les données.