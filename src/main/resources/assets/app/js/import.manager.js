var ImportManager = function($, input, modal, callback){
    input         = $(input);
    var container = input.parents('.modal-body');
    var inputOld  = input.clone();
    var loader    = $(document.createElement('i'));
    loader.addClass('fa-spinner-animated');
    loader.addClass('fa-spinner');
    loader.addClass('fa');

    var beforeUpload = function(){
        container.html('');
        container.append(loader);
    }

    var afterUpload = function(result){
        result = JSON.parse(result);
        if(result.isOk){
            container.html('');
            container.append(inputOld);
            defineEvents();
            if(callback !== null) callback(result);
            if(modal !== null) modal.modal('hide');
        }
    }

    var defineEvents = function(){
        input.change(function(event){
            var form = new FormData();
            form.append('fileUpload', event.target.files[0]);
            upload(form, beforeUpload, afterUpload);
        });
    }

    var upload = function(formData, before, after){
        $.ajax({
            url: '/import',
            data: formData,
            processData: false,
            contentType: false,
            type: 'POST',
            success: after,
            beforeSend: before
        });
    }
    defineEvents();
}