# Extract main topic of a website

Goal: 
This project is to discover the main topic or the keywords of a website. 

You can input the test cases URL built in the project, or input your own URL.

After the process, the application will return five keywords to describe the website. 

Explanation:
The top 5 keywords are from different source.
1. URL - I extract some keywords from the URL file name and query
2. HTML meta data - some websites provide their keywords, description and name in HTML meta data
3. HTML heading tags - the heading tags usually present the main topic of the website.

During the extraction, the CoreNLP library is used to identify the Noun and ignore the other words.

Finally, all the words are sorted based on their frequencies. The top five words are treated as the main topic of the website. 

Future:
There are some improvements that can be done. 

First, add different weights for words. The words in the meta data should be more important thant the words in the URL.
So, I can add a higher weight for those words.

Second, discover more powerful annotators in the CoreNLP. 

Third, improve the performance of the app. 