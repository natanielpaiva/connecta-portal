
<!DOCTYPE html>

<html lang="en" ng-controller="appMain">
    <head>
        <meta charset="utf-8" />

        <title>${project.name}</title>

        <link rel="stylesheet" href="assets/css/app.css" />
        <link rel="icon" href="assets/img/favicon-128.png" />
    </head>
    <body>

        <div ng-cloak app-layout></div>
        
        <script type="text/javascript" src="bower_components/pace/pace.js"></script>
        <script type="text/javascript" src="bower_components/requirejs/require.js" data-main="app/main.js"></script>
        
        <script type="text/ng-template" id="portal-validation-messages">
            <p ng-message="required" class="help-block">
                {{'validation.required' | translate}}
            </p>
            <p ng-message="email" class="help-block">
                {{'validation.email' | translate}}
            </p>
            <p ng-message="minlength" class="help-block">
                {{'validation.minlength' | translate:{minlength:getAttr('minlength')} }}
            </p>
            <p ng-message="maxlength" class="help-block">
                {{'validation.maxlength' | translate}}
            </p>
        </script>
    </body>
</html>