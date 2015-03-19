define([
    'connecta.speaknow'
], function (presenter) {
    return presenter.lazy.service('CompanyService', function (speaknowResources, $http, $upload) {

        this.list = function (page, size) {
            var url = speaknowResources.company + "/list?page=" + page + "&size=" + size;
            return $http.get(url);
        };

        this.save = function (fileQuad, fileRect, company) {
            var url = speaknowResources.company + "/save";
            var fd = new FormData();
            fd.append('fileQuad', fileQuad);
            fd.append('fileRect', fileRect);
            fd.append('company', JSON.stringify(company));
            return $http.post(url, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        };

        this.getQuestionTypes = function () {
            var url = speaknowResources.interaction + "/poll/question/types";
            return $http.get(url);
        };
    });
});