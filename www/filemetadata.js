const exec = require('cordova/exec');

// Définition du plugin
const FileMetadata = {
    // Fonction pour obtenir les métadonnées d'un fichier
    getFileMetadata: function(filePath, successCallback, errorCallback) {
        // Vérification des paramètres
        if (typeof filePath !== 'string' || filePath.trim() === '') {
            errorCallback('Invalid file path');
            return;
        }
        
        // Appel de la méthode native du plugin
        exec(
            successCallback, // Callback en cas de succès
            errorCallback,   // Callback en cas d'erreur
            'FileMetadataPlugin', // Nom du service natif (défini dans plugin.xml)
            'getFileMetadata',     // Action à exécuter sur le service
            [filePath]             // Tableau des arguments à passer
        );
    }
};

module.exports = FileMetadata;
