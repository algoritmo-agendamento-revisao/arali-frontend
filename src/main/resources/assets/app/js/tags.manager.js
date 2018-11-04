var TagsManager = function($, modal, table, resourceManager, callback){
    var tableManager  = new TableManager(table);
    var selectedTags  = [];
    var allTags       = [];
    var btnSave       = modal.find('button[name=save]');
    var self          = this;

    resourceManager.findAll(null, function(tags){
        tags          = JSON.parse(tags);
        var select    = modal.find('select[name=tag]');
        if(Array.isArray(tags)){
            tags.forEach(function(tag){
                var option = $(document.createElement('option'));
                option.text(tag.label);
                option.val(tag.id);
                select.append(option);
            });
        }
        allTags = tags;
    });

    var findTag  = function(id){
        var tags = allTags.filter(function(tag) { return tag.id == id; });
        return (tags.length > 0) ? tags[0] : null; 
    }

    btnSave.click(function(){
        var tag = findTag(modal.find('option:selected').val());
        if(tag !== null && typeof tag !== 'undefined'){
            selectedTags.push(tag);
            update();
            modal.modal('hide');
        }
        return false;
    });

    var updateTable = function(tag, index){
        tableManager.addRow(tag, function(){
            index = $(this).attr('data-index');
            selectedTags = selectedTags.filter(function(value){
                var item = findTag(index);
                return value.id != item.id;
            });
            update();
            return false;
        });
    }

    var clear = function(el){
        el.html('');
    }

    var update = function (){
        clear(table);
        if(Array.isArray(selectedTags)){
            selectedTags.forEach(function(tag, index){
                updateTable(tag, index);
            });
        }
        callback(self.getData());
    }

    this.getData = function(){
        return {
            name: 'tags',
            value: selectedTags
        };
    }
}