/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
module.exports = function (grunt) {
    grunt.loadNpmTasks('grunt-contrib-jshint');
    
    grunt.initConfig({
        jshint: {
            files: ['Gruntfile.js', 'src/main/webapp/**.js','src/test/javascript/**.js']
        }
    });
    
    grunt.registerTask('default', ['jshint']);
};
