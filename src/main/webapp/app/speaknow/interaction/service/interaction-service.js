define([
    'connecta.speaknow'
], function (presenter) {
    return presenter.lazy.service('InteractionService', function (speaknowResources, $http, $upload) {

        this.list = function () {
            var url = speaknowResources.interaction;
            return $http.get(url);
        };

        this.save = function (interaction) {
            var url = speaknowResources.interaction;
            return $http.post(url, interaction);
        };

        this.upload = function (file, company) {
            var url = speaknowResources.company + "/save";
            return $upload.upload({
                url: url,
                method: 'POST',
                fields: {
                    'company': company
                },
                file: file
            }).progress(function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
            }).success(function (data, status, headers, config) {
                console.log(data);
            });
        };

        this.getQuestionTypes = function () {
            var url = speaknowResources.interaction + "/poll/question/types";
            return $http.get(url);
        };

    });
});