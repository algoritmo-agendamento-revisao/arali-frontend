var OptionsManager = function($, modal, table, list, callback){
    var selectedOptions = [];
    var btnSave = modal.find('button[name=save]');
    var input   = modal.find('input[name=answer]');
    var self    = this;

    btnSave.click(function(){
        var answer = input.val();
        if(answer !== null && typeof answer !== 'undefined' && answer.length > 0){
            selectedOptions.push(answer);
            update();
            modal.modal('hide');
            input.val('');
        }
        return false;
    });

    var updateTable = function(option, index){
        var row  = $(document.createElement('tr'));
        var col1 = $(document.createElement('td'));
        var col2 = $(document.createElement('td'));
        var col3 = $(document.createElement('td'));
        
        var btnRemove = $(document.createElement('button'));
        btnRemove.text('-');
        btnRemove.attr('data-index', index);
        btnRemove.addClass('btn');
        btnRemove.addClass('btn-danger');
        row.append(col1);
        row.append(col2);
        row.append(col3);
        col1.text(index + 1);
        col2.text(option);
        col3.append(btnRemove);
        table.prepend(row);
        btnRemove.click(function(){
            index = $(this).attr('data-index');
            selectedOptions = selectedOptions.filter(function(value){
                return value != selectedOptions[index];
            });
            update();
            return false;
        });
    }

    var updateList = function(value, index){
        var option  = $(document.createElement('option'));
        option.val(index);
        option.text(value);
        list.prepend(option);
    }

    var clear = function(el){
        el.html('');
    }

    var update = function (){
        clear(table);
        clear(list);
        if(Array.isArray(selectedOptions)){
            selectedOptions.forEach(function(option, index){
                updateTable(option, index);
                updateList(option, index);
            });
        }
        callback(self.getData());
    }

    this.getData = function(){
        return {
            name: 'options',
            value: selectedOptions
        };
    }

    this.getPrepData = function(){
        return function(data){
            if(data.options){
                if(Array.isArray(data.options) && typeof data.options[0] === 'string'){
                    data.optionCorrect = {
                        value: data.options[data.optionCorrect]
                    };
                    data.options.forEach(function(value, index){
                        data.options[index] = {
                            value: value
                        };
                    });
                }else{
                    data.optionCorrect = data.options[data.optionCorrect];
                }
            }
            return data;
        }
    }
}