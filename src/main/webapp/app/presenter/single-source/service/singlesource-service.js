define([
    'connecta.presenter'
], function (presenter) {

    return presenter.lazy.service('SingleSourceService', function (presenterResources, $upload) {
        this.save = function (file, singlesource) {

            $upload.upload({
                url: presenterResources.singlesource + "/file",
                fields: {
                    singlesource: singlesource
                },
                file: file
            }).progress(function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
            }).success(function (data, status, headers, config) {
                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            });
        };
    });

});