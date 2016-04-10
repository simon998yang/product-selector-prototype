var gulp = require('gulp');
var sass = require('gulp-sass');
var autoprefixer = require('gulp-autoprefixer');

var config = {
	bootstrapDir : './bower_components/bootstrap-sass',
	publicDir : './src/main/resources/static',
};

var autoprefixerOptions = {
	browsers : [ 'last 10 versions', '> 1%', 'Firefox ESR' ]
};

var input = './sass/*.scss';

gulp.task('css', function() {
	return gulp.src(input).pipe(sass({
		includePaths : [ config.bootstrapDir + '/assets/stylesheets' ],
	})).pipe(autoprefixer()).pipe(gulp.dest(config.publicDir + '/css'));
});

gulp.task('watch', function() {
	return gulp
	// Watch the input folder for change,
	// and run `sass` task when something happens
	.watch(input, [ 'css' ])
	// When there is a change,
	// log a message in the console
	.on(
			'change',
			function(event) {
				console.log('File ' + event.path + ' was ' + event.type
						+ ', running tasks...');
			});
});

gulp.task('default', [ 'css','watch' ]);
