var ResourceManager = function($, resourceName){
    this.create = function(obj, before, after, complete){
        $.ajax({
            url: resourceName,
            data: JSON.stringify(obj),
            method: 'post',
            beforeSend: before,
            success: after,
            complete: complete
        });
    }

    this.findAll = function(before, after, complete){
        $.ajax({
            url: resourceName,
            method: 'get',
            beforeSend: before,
            success: after,
            complete: complete
        });
    }

    this.findAllCustom = function(params, before, after, complete){
        $.ajax({
            url: resourceName + '/not/' + params['not'],
            method: 'get',
            beforeSend: before,
            success: after,
            complete: complete
        });
    }

    this.find = function(id, before, after){
        $.ajax({
            url: resourceName + "/" + id,
            method: 'get',
            beforeSend: before,
            success: after             
        });
    }

    this.delete = function(id, before, after){
        $.ajax({
            url: resourceName + "/" + id,
            method: 'delete',
            beforeSend: before,
            success: after             
        });
    }

}