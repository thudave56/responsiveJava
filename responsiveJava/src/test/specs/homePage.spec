@objects
    comments            #comments
    article-content     div.article

= Main section =
    @on mobile, tablet 
        comments:
            width 300px 
            inside screen 10 to 30px top right 
            near article-content > 10px right 

    @on desktop
        comments:
            width ~ 100% of screen/width 
            below article-content > 20px 