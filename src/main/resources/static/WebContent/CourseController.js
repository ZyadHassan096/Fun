var app = angular.module("phase2" , ['ngRoute'])

app.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider){
    $routeProvider
		.when("http://localhost:8060/TheAngular_Project/CreateCoursePage.html", {
			templateUrl: "CreateCoursePage.html",
			controller: "CreateCourse"
		})
		.when("http://localhost:8060/TheAngular_Project/CoursesPage.html", {
			templateUrl: "CoursesPage.html",
			controller: "LoadCourses"
		})
		.when("http://localhost:8060/TheAngular_Project/CoursesPage.html", {
			templateUrl: "CoursesPage.html",
			controller: "ShowCourses"
		})
		
		// .otherwise({ redirectTo: '/'})
		;
}]);


app.controller( "CreateCourse" ,function ($scope , $http , $location)
		{
			$scope.save = function()
			{
				var CourseName= document.getElementById("CName").value;
				var CourseID= document.getElementById("CID").value;
				var TeacherName= document.getElementById("TeacherName").value;
												
				$http.get('http://localhost:8090/CreateCourse/'+CourseName+'/'+CourseID+'/'+TeacherName)
				.then(function(response)
					{
					var flag=JSON.stringify(response.data);
					if (flag.match("true"))
						{
						alert("Course Added successfully")
						}
					else if(flag.match("false"))
						{
						alert("ERROR ,, Inavlid Data")
						}
					
					});
				
			}
		});

app.controller( "ShowCourses" , function($scope , $http , $location ,srvShareData)
		{
			$scope.dataToShare = [];
			var Info ;
			$scope.save = function()
			{
				$http.get('http://localhost:8090/ShowCourses')
				.then(function(response)
					{
					Info = JSON.stringify(response.data);
					$scope.dataToShare = JSON.parse(Info) ;
					srvShareData.addData($scope.dataToShare);
					
					window.location.href="http://localhost:8060/TheAngular_Project/CoursesPage.html";
					});
			}
		});


app.controller("LoadCourses" , function($scope,  srvShareData )
		{
		
				$scope.Courses = srvShareData.getData();
				
		});

app.service('srvShareData', function($window) {
    var KEY = 'App.SelectedValue';

    var addData = function(newObj) {
    	//$window.sessionStorage.clear();
        var mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        } else {
            mydata = [];
        }
        mydata = newObj;
       
        $window.sessionStorage.setItem(KEY, JSON.stringify(mydata));
    };

    var getData = function(){
        var mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        }
        
        return mydata || [];
    };

    return {
        addData: addData,
        getData: getData
    };
});
