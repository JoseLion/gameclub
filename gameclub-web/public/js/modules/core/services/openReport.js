angular.module('Core').factory('openReport', function(urlReportsPath, notif, $window) {

    function concatFinalUrl(reportName, params) {
        let finalUrl = urlReportsPath.url.concat(reportName).concat('.rptdesign');
        angular.forEach(params, function(value, key) {
            finalUrl = finalUrl.concat('&').concat(key).concat('=').concat(value);
        });
        return finalUrl;
    }

    return {
        pdf: function(reportName, params) {
            let finalUrl = concatFinalUrl(reportName, params);
            finalUrl = finalUrl.concat('&__format=pdf');
            $window.open(finalUrl);
        },
        excel: function(reportName, params) {
            let finalUrl = concatFinalUrl(reportName, params);
            finalUrl = finalUrl.concat('&__format=xlsx');
            $window.open(finalUrl);
        },
        word: function(reportName, params) {
            let finalUrl = concatFinalUrl(reportName, params);
            finalUrl = finalUrl.concat('&__format=docx');
            $window.open(finalUrl);
        }
    }
});
