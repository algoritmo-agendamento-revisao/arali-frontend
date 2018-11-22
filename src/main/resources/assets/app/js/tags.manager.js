var TagsManager  = function($, list, resourceManager){
    var self     = this;
    var attempts = 3;

    var getTags = function(){
        resourceManager.findAll(null, function(tags){
            try{
                tags          = JSON.parse(tags);
                var select    = $(list);
                if(Array.isArray(tags)){
                    tags.forEach(function(tag){
                        var option = $(document.createElement('option'));
                        option.text(tag.label);
                        option.val(tag.id);
                        select.append(option);
                    });
                }else{
                    throw new Error('Request error!');
                }
            }catch(e){
                if(attempts > 0 && tags.status >= 500){
                    attempts--;
                    setTimeout(getTags, 1000);
                }
            }
        }, function(xhr){
            if(attempts > 0 && xhr.status >= 500){
                attempts--;
                setTimeout(getTags, 1000);
            }
        });
    }
    getTags();
}