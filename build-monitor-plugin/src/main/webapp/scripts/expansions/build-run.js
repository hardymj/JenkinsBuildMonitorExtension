'use strict';

angular.

module('build-monitor.expansions.build-run', []).

directive('buildRun', [function ($scope) {

    return {
        restrict: 'E',
        replace:  true,
        scope: {
            project: '='
        },
        link:function(scope, element) {
            element.click(function(){
                var jobName=scope.project.name;
                //var runTestsString = "job/"+jobName+"/build?token="+jobName;
                var runTestsString = "job/"+jobName+"/build?token=RunTests";
                window.fetch(runTestsString);
            });
        },
        template: [            
            '<div>',
                '<div data-ng-show="!!project.lastCompletedBuild.timeElapsedSince && project.progress == 0">',
                    '<a',
                    'data-ng-show="!!project.lastCompletedBuild.name"',
                    'class="build-name"',
                    'ng-click="runTests();"',
                    'title="Details of {{project.name}}, build {{project.lastCompletedBuild.name}}"',
                    '>',
                        '<span>',
                            'Run Tests',
                        '</span>',
                    '</a>',
                '</div>',                
            '</div>',
        ].join('\n')
    }
}]);