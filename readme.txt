The script use these variables in the config files:

normal 
error 
confidence 
names 
sampleSize 
ciFiles
ciHeaderRowIndex

The script is run on the files in "ciFiles".
It starts on the row in "ciHeaderRowIndex".
The size of the sample is either given in "sampleSize" or
it is calculated using "normal", "error", and "confidence".
To have the sample size calculated set the "sampleSize" to 0
The sample size is divided by the number of names in "names".
This gives a number of entries per name.
The script randomly assigns names to rows. 
Two columns are generated in the file; QA and assigned.
QA indicates if the row has been assigned to QA.
Assigned gives the specific name.
The scripts asks for a config file and then modifies the specified xlsx file.