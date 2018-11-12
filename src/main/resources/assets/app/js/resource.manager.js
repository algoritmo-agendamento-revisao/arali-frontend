var ResourceManager = function($, resourceName){
    this.create = function(obj, before, after){
        $.ajax({
            url: resourceName,
            data: JSON.stringify(obj),
            method: 'post',
            beforeSend: before,
            success: after             
        });
    }

    this.findAll = function(before, after){
        $.ajax({
            url: resourceName,
            method: 'get',
            beforeSend: before,
            success: after             
        });
    }

    this.findAllCustom = function(params, before, after){
        $.ajax({
            url: resourceName + '/not/' + params['not'],
            method: 'get',
            beforeSend: before,
            success: after
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