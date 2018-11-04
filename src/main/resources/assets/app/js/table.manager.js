var TableManager = function(table){
    this.clean  = function(){
        table.html('');
    }
    this.addRow = function(obj, actionRemove){
        var row  = $(document.createElement('tr'));
        var col1 = $(document.createElement('td'));
        var col2 = $(document.createElement('td'));
        var col3 = $(document.createElement('td'));
        var btnRemove = $(document.createElement('a'));
        btnRemove.attr('href', '#');
        btnRemove.text('-');
        btnRemove.attr('data-index', obj.id);
        btnRemove.addClass('btn');
        btnRemove.addClass('btn-danger'); 
        btnRemove.click(actionRemove);
        col3.append(btnRemove);
        col1.text(obj.id);
        col2.text(obj.label);
        row.append(col1);
        row.append(col2);
        row.append(col3);
        table.prepend(row);
    }
}