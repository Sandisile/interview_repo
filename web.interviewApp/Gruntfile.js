/*global module:false*/
	module.exports = function(grunt) {
	
	  // Project configuration.
	  grunt.initConfig({
	      pkg: grunt.file.readJSON('package.json'),
	
	    // Metadata.
	    meta: {
	      version: '0.2.0'
	    },
	    banner: '/*! <%= pkg.name %> - v<%= meta.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/\n',
	    // Task configuration.
	    clean: {
	      build: {
	    	  src: ['dist/**']
	      }
	    },
	    concat: {
	      options: {
	        banner: '<%= banner %>',
	        stripBanners: true,
	        separator: '\n'
	      },
	      dist: {
	        src: ['web/app/**/*.js'],
	        dest: 'dist/web/<%= pkg.name %>.js'
	      }
	    },
	    uglify: {
	      options: {
	        banner: '<%= banner %>',
	        mangle: false,
	        sourceMap: false
	      },
	      dist: {
	        src: '<%= concat.dist.dest %>',
	        dest: 'dist/web/<%= pkg.name %>.js'
	      }
	    },
	    copy: {
	      dist: {
	        files: [
	          {expand: true, src: ['web/favicon.ico', 'web/app/index.html', 'web/WEB-INF/web.xml'], dest: 'dist/'},
	          {expand: true, src: ['web/app/scripts/*.js', 'web/content/js/angular-csp.css', 'web/app/stylesheets/*.css', 'web/app/fonts/*.*', 'web/app/images/*.*'], dest: 'dist/', filter: 'isFile'},
	          {expand: true, src: ['web/app/**/*.html', 'web/app/shell/navigation.css'], dest: 'dist/', filter: 'isFile'}
	        ]
	      }
	    },
	    cssmin : {
	      target: {
	    	  files: [{
	          expand: true,
	          cwd: 'dist/web',
	          src: ['**/*.css', '!**/*.min.css'],
	          dest: 'dist/web'
	        }]
	      }
	    },
	    htmlmin : {
	      target: {
	        options: {
	          removeComments: true,	          collapseWhitespace: true
	        },
	        files: {
	          'dist/web/app/index.html': 'web/app/index.html'
	        }
	      }
	    },
	    imagemin: {
	      dynamic: {
	        files: [{
	          expand: true,
	          cwd: 'dist/web',
	          src: ['**/*.{png,jpg,gif}'],
	          dest: 'dist/web'
	        }]
	      }
	    },
	    useminPrepare: {
	      html: 'dist/web/app/index.html'
	    },
	    usemin: {
	      html: 'dist/web/app/index.html',
	    },
	    jshint: {
	      options: {
	        jshintrc: true,
	      },
	      all: {
	        src: ['web/app/**/*.js', 'test/src/**/*.js']
	      },
	      gruntfile: {
	        src: ['Gruntfile.js']
	      }
	    },
	    csslint: {
	      options: {
	        csslintrc: '.csslintrc'
	      },
	      strict: {
	        options: {
	          import: 2
	        },
	        src: ['web/**/*.css']
	      },
	      lax: {
	        options: {
	          import: false
	        },
	        src: ['web/**/*.css']
	      }
	    },
	    karma: {
	      unit: {
	        configFile: 'karma.conf.js'
	      }
	    },
    watch: {
	      jshint: {
	        files: '<%= jshint.all.src %>',
	        tasks: ['jshint:all']
	      }
	    }
	  });
	
	  // These plugins provide necessary tasks.
	  grunt.loadNpmTasks('grunt-contrib-clean');
	  grunt.loadNpmTasks('grunt-contrib-concat');
	  grunt.loadNpmTasks('grunt-contrib-uglify');
	  grunt.loadNpmTasks('grunt-contrib-cssmin');
	  grunt.loadNpmTasks('grunt-contrib-htmlmin');
	  grunt.loadNpmTasks('grunt-contrib-imagemin');
	  grunt.loadNpmTasks('grunt-contrib-copy');
	  grunt.loadNpmTasks('grunt-usemin');
	  
	  grunt.loadNpmTasks('grunt-karma');
	  grunt.loadNpmTasks('grunt-contrib-watch');
	  
	  grunt.loadNpmTasks('grunt-contrib-jshint');grunt 
	  grunt.loadNpmTasks('grunt-contrib-csslint');
	
	  // Tasks.
	  grunt.registerTask('dev', ['jshint']);
	  grunt.registerTask('default', ['clean', 'copy', 'cssmin', 'imagemin', 'build']);
	  grunt.registerTask('build', [
	    'useminPrepare',
	    'concat:dist',
	    'uglify:dist',
	    'usemin'
	  ]);
	
	};
