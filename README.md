# mini-twitter

Implementation of Project A2 for CS356.

# Notes

Because I used an API/Database model to simulate the Twitter model,
I was unable to implement a proper visitor model. If I had more time, I would
retool the system to utilize tree better (or not at all, implementing TreeNode
was a mistake) to get rid of all the wasted overhead.

The only criteria for being a positive message was containing the letter
sequence "meme" somewhere in the message body.
