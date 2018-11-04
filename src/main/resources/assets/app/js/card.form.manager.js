var FormManager  = function($, form, resourceManager, beforeSubmit, afterSubmit){
    var moreData = {};
    var prepData = null;

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
        data     = addMoreData(toObject(data));
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
}