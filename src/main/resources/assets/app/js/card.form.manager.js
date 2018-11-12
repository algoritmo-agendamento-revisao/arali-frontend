var FormManager  = function($, form, resourceManager, beforeSubmit, afterSubmit){
    var moreData = {};
    var prepData = null;
    var fCleanData = null;

    var toObject = function(dataArray){
        var obj = new Object();
        dataArray.forEach(function(value){
            obj[value.name] = value.value;
        });
        return obj;
    }

    this.setPrepData = function(prep){
        prepData = prep;
    }

    form.submit(function(){
        var data = form.serializeArray();
        data     = cleanData(addMoreData(toObject(data)));
        if(prepData != null){
            data = prepData(data);
        }
        resourceManager.create(data, beforeSubmit, afterSubmit);
        return false;
    });

    this.addDataToForm = function(data){
        moreData[data.name] = data.value;
    }
    
    var addMoreData = function(data){
        Object.keys(moreData).forEach(function(index){
            data[index] = moreData[index];
        });
        return data;
    }

    this.setFCleanData = function(callback){
        if(callback !== null){
            fCleanData = callback;
        }
    }

    var cleanData = function(data){
        if(fCleanData != null){
            var result = fCleanData(data);
            if(result != null && typeof result !== 'undefined'){
                data = result;
            }
        }
        return data;
    }
}