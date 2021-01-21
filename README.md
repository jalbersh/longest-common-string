### Longest Common Substring (LCS) Server
##Overview
A simple web application that allows a user to request the longest
common substring given a list of strings.

##Functional Requirements
Solve the Longest Common Substring problem via HTTP POST\
A user should be able to request the LCS of a Set of Strings by sending a POST request\
to the server at http://<host>/lcs . The body of the POST request must be a JSON\
object representing a Set of strings in the following format:\
{\
"setOfStrings": [\
{"value": "comcast"},\
{"value": "communicate"},\
{"value": "commutation"}\
]\
}\
The server should enforce the following rules upon receiving a request to solve LCS:\
If there is no POST body in the request or if the POST body is not in the correct\
format the server should respond with an appropriate HTTP status code, and a\
response body explaining that the format of the request was not acceptable.\
If setOfStrings is empty the server should respond with an HTTP an appropriate\
status code with a response body explaining that setOfStrings should not be\
empty.\
if the setOfStrings supplied is not a set (i.e. all strings are not unique) the server\
should respond with an appropriate HTTP status code, and a response\
body explaining that "setOfStrings" must be a Set\
If the above conditions are met the server should invoke your algorithm to find the\
longest common substring of the "values" in the POST body. For example if the POST\
body was:\
{\
"setOfStrings": [\
{"value": "comcast"},\
{"value": "comcastic"},\
{"value": "broadcaster"}\
]\
}\
the longest common substring would be cast . If there is more than one LCS return\
them all in alphabetic order.\
Once the server has computed the Longest Common Substring it should respond with an\
appropriate HTTP status code and a body in the following format:\
{\
"lcs": [\
{"value": "cast"}\
]\
}\
@#Bonus
The following are not required but might be nice additions to the exercise:\
Make a Homepage for your server that contains a form that when submitted makes\
the /lcs POST request.\
Create a script that exercises and verifies the functionality of your server by making\
HTTP requests and verifying that the responses are appropriate.\
