var app = angular.module("phase2" , ['ngRoute'])

app.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider){
    $routeProvider
		.when("http://localhost:8060/TheAngular_Project/GameForm.html", {
			templateUrl: "GameForm.html",
			controller: "AddGame"
		})
		.when("http://localhost:8060/TheAngular_Project/MCQ_QuestionForm.html", {//load Game Name
			templateUrl: "MCQ_QuestionForm.html",
			controller: "Load"
		})
		.when("http://localhost:8060/TheAngular_Project/MCQ_QuestionForm.html", {
			templateUrl: "MCQ_QuestionForm.html",
			controller: "AddMCQ_Question"
		})
		.when("http://localhost:8060/TheAngular_Project/MCQ_QuestionForm.html", {
			templateUrl: "MCQ_QuestionForm.html",
			controller: "Finish"
		})
		.when("http://localhost:8060/TheAngular_Project/TF_QuestionForm.html", {
			templateUrl: "TF_QuestionForm.html",
			controller: "Load"
		})
		.when("http://localhost:8060/TheAngular_Project/TF_QuestionForm.html", {
			templateUrl: "TF_QuestionForm.html",
			controller: "AddTF_Question"
		})
		.when("http://localhost:8060/TheAngular_Project/TF_QuestionForm.html", {
			templateUrl: "TF_QuestionForm.html",
			controller: "FinishQ"
		})
		
		// .otherwise({ redirectTo: '/'})
		;
}]);


app.controller( "AddGame" ,function ($scope , $http, srvShareData , $location)
		{
			$scope.save = function() {
				var email= document.getElementById("email").value;
				var GameName= document.getElementById("GameName").value;
				var CourseName= document.getElementById("CourseName").value;
				var Schemas=document.getElementById("GameSchema");
				var Schema=Schemas.elements["GameSchema"].value ;
				$http.get('http://localhost:8090/AddGame/'+GameName+'/'+Schema+'/'+email+'/'+CourseName)
				.then(function(response)
					{
					var Info = JSON.stringify(response.data);
					$scope.data=JSON.parse(Info) ;
					srvShareData.addData(GameName);
					if ($scope.data=="true")
					{
						if (Schema=="TF")
							{
							window.location.href="http://localhost:8060/TheAngular_Project/TF_QuestionForm.html";
							}
						else if (Schema=="MCQ")
							{
							window.location.href="http://localhost:8060/TheAngular_Project/MCQ_QuestionForm.html";
							}
					
					}
					else
					{
						alert("Invalid Game Name")
					}
					});
				}
		});

app.controller( "AddMCQ_Question" ,function ($scope , $http, srvShareData , $location)
		{
			$scope.send = function(GameName) {
				var Question= document.getElementById("Question").value;
				var FirstChoice= document.getElementById("FirstChoice").value;
				var SecondChoice= document.getElementById("SecondChoice").value;
				var ThirdChoice= document.getElementById("ThirdChoice").value;
				var FourthChoice= document.getElementById("FourthChoice").value;
				var Answer= document.getElementById("Answer").value;
				$http.get('http://localhost:8090/AddMCQ_Question/'+Question+'/'+FirstChoice+'/'+
						SecondChoice+'/'+ThirdChoice+'/'+FourthChoice+'/'+Answer+'/'+GameName)
				.then(function(response)
					{
					window.location.href="http://localhost:8060/TheAngular_Project/MCQ_QuestionForm.html";					
					});
				}
		});

app.controller( "Finish" ,function ($scope , $http, srvShareData , $location)
		{
			$scope.finish = function(GameName) {
				var Question= document.getElementById("Question").value;
				var FirstChoice= document.getElementById("FirstChoice").value;
				var SecondChoice= document.getElementById("SecondChoice").value;
				var ThirdChoice= document.getElementById("ThirdChoice").value;
				var FourthChoice= document.getElementById("FourthChoice").value;
				var Answer= document.getElementById("Answer").value;
				$http.get('http://localhost:8090/AddMCQ_Question/'+Question+'/'+FirstChoice+'/'+
						SecondChoice+'/'+ThirdChoice+'/'+FourthChoice+'/'+Answer+'/'+GameName)
				.then(function(response)
					{
					alert(GameName+" Added Successfully")					
					});
				}
		});


app.controller( "AddTF_Question" ,function ($scope , $http, srvShareData , $location)
		{
			$scope.send_Q = function(GameName) {
				var Question= document.getElementById("Question").value;
				var Answer= document.getElementById("Answer").value;
				$http.get('http://localhost:8090/AddTF_Question/'+Question+'/'+Answer+'/'+GameName)
				.then(function(response)
					{
					window.location.href="http://localhost:8060/TheAngular_Project/TF_QuestionForm.html";					
					});
				}
		});


app.controller( "FinishQ" ,function ($scope , $http, srvShareData , $location)
		{
			$scope.finishQ= function(GameName) {
				var Question= document.getElementById("Question").value;
				var Answer= document.getElementById("Answer").value;
				$http.get('http://localhost:8090/AddTF_Question/'+Question+'/'+Answer+'/'+GameName)
				.then(function(response)
					{
					alert(GameName+" Added Successfully")					
					});
				}
		});

app.controller("Load" , function($scope,  srvShareData )
		{
				
				$scope.GameName = srvShareData.getData();
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


