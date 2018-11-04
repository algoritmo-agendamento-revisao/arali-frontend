var DecksManager = function($, modal, table, resourceManager, callback){
    var tableManager  = new TableManager(table);
    var selectedDecks = [];
    var allDecks      = [];
    var btnSave       = modal.find('button[name=save]');
    var self          = this;

    resourceManager.findAll(null, function(decks){
        decks          = JSON.parse(decks);
        var select     = modal.find('select[name=deck]');
        if(Array.isArray(decks)){
            decks.forEach(function(deck){
                var option = $(document.createElement('option'));
                option.text(deck.label);
                option.val(deck.id);
                select.append(option);
            });
        }
        allDecks = decks;
    });

    var findDeck = function(id){
        var decks = allDecks.filter(function(deck) { return deck.id == id; });
        return (decks.length > 0) ? decks[0] : null;
    }

    btnSave.click(function(){
        var deck = findDeck(modal.find('option:selected').val());
        if(deck !== null && typeof deck !== 'undefined'){
            selectedDecks.push(deck);
            update();
            modal.modal('hide');
        }
        return false;
    });

    var updateTable = function(deck, index){
        tableManager.addRow(deck, function(){
            index = $(this).attr('data-index');
            selectedDecks = selectedDecks.filter(function(value){
                var item = findDeck(index);
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
        if(Array.isArray(selectedDecks)){
            selectedDecks.forEach(function(deck, index){
                updateTable(deck, index);
            });
        }
        callback(self.getData());
    }

    this.getData = function(){
        return {
            name: 'decks',
            value: selectedDecks
        };
    }
}