<!DOCTYPE html>
<html lang="en" ng-controller="appMain">
    <head>
        <meta charset="utf-8">
        <title>Connecta Portal</title>
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="assets/css/grid.css"/>
        <link rel="stylesheet" href="assets/css/forms.css"/>
        <link rel="stylesheet" href="assets/css/style.css"/>
        <link rel="stylesheet" href="assets/css/app-header.css"/>
        <link rel="stylesheet" href="assets/css/app-search.css"/>
        <link rel="stylesheet" href="assets/css/app-pages.css"/>
        <link rel="stylesheet" href="assets/css/app-sidebar.css"/>
        <link rel="stylesheet" href="assets/css/pace-flash.css"/>

        <!-- Estilos personalizados -->
        <link ng-repeat="stylesheet in stylesheets" ng-href="{{stylesheet}}" type="text/css" rel="stylesheet" />
    </head>
    <body>

        <div app-layout></div>

        <script type="text/javascript" src="bower_components/pace/pace.js"></script>
        <script type="text/javascript" src="bower_components/requirejs/require.js" data-main="app/main.js"></script>
    </body>
</html>