module.exports = function (grunt) {
    grunt.initConfig({
        jshint: {
            jshintrc: '.jshintrc',
            files: ['app/**/*.js']
        },
        sass: {
            dist: {
                options: {
                    style: 'compressed'
                },
                files: [{
                    expand: true,
                    cwd: 'assets/scss',
                    src: ['**/**.scss'],
                    dest: 'assets/css',
                    ext: '.css'
                }]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-sass');

    grunt.registerTask('default', ['jshint', 'sass']);
};
