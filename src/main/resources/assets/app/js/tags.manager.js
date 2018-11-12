var TagsManager = function($, list, resourceManager){
    var self  = this;
    resourceManager.findAll(null, function(tags){
        tags          = JSON.parse(tags);
        var select    = $(list);
        if(Array.isArray(tags)){
            tags.forEach(function(tag){
                var option = $(document.createElement('option'));
                option.text(tag.label);
                option.val(tag.id);
                select.append(option);
            });
        }
    });
}