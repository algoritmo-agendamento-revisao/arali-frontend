var CardManager = function($, table, resourceManager){
    var tableManager  = new TableManager(table);
    var self          = this;

    resourceManager.findAll(null, function(cards){
        cards          = JSON.parse(cards);
        if(Array.isArray(cards)){
            cards.forEach(function(card){

                var row  = $(document.createElement('tr'));
                var col1 = $(document.createElement('td'));
                var col2 = $(document.createElement('td'));
                var col3 = $(document.createElement('td'));
                var col4 = $(document.createElement('td'));
                var col5 = $(document.createElement('td'));

                col1.text(card.id);
                col2.text(card.difficulty);
                col3.text(card.question);
                col4.text(card.optionCorrect.value);
                col5.text(card.tag.label);

                row.append(col1);
                row.append(col2);
                row.append(col3);
                row.append(col4);
                row.append(col5);
                table.prepend(row);


            });
        }
    });

}