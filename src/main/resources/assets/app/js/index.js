var Card = function($){
    var createDiv =  function() { return $(document.createElement('div')) };

    this.setLabel = function(value){
        this.label = value;
        return this;
    }

    this.setTotalCards = function(value){
        this.totalCards = value;
        return this;
    }

    this.setQtyStudiedCards = function(value){
        this.qtyStudiedCards = value;
        return this;
    } 
    
    this.setLastStudy = function(value){
        this.lastStudy = value;
        return this;
    }

    this.setId = function(id){
        this.id = id;
        return this;
    }
    
    this.render = function(block){
        var card  = createDiv();
        var body  = createDiv();
        var title = $(document.createElement('h5'))
        var text  = $(document.createElement('p'))
        var btn   = $(document.createElement('a'));
        card.addClass('card');
        body.addClass('card-body');
        title.addClass('card-title');
        text.addClass('card-text');
        btn.addClass('btn');
        btn.addClass('btn-primary');
        btn.text('Estudar');
        btn.attr('href', 'study/' + this.id);
        card.append(body);
        body.append(title);
        body.append(text);
        body.append(btn);
        title.text(this.label);
        text.html(
            "Cards estudados " + 
            this.qtyStudiedCards + "/" + 
            this.totalCards + "<br />" + 
            this.lastStudy
        );
        block.append(card);
        return this;
    }
}

$ = jQuery;
$(function(){
    moment.locale('pt-BR');
    var cardGroup       = $(".card-group");
    var resourceManager = new ResourceManager($, "decks");
    var clearCardGroup  = function() { cardGroup.html(''); }
    var loadDecks       = function(decks){
        setTimeout(null, 1000);
        clearCardGroup();
        decks = JSON.parse(decks);
        if(Array.isArray(decks)){
            decks.forEach(deck => {
                var card = new Card($);
                card
                    .setLabel(deck.label)
                    .setLastStudy(
                        moment(deck.lastStudy).format('L')
                    )
                    .setQtyStudiedCards(deck.qtyStudiedCards)
                    .setTotalCards(deck.totalCards)
                    .setId(deck.id)
                    .render(cardGroup);
            });
        }
    }
    var beforeLoad = function(){
        clearCardGroup();
        var container = $(document.createElement('div')); 
        var spinner   = $(document.createElement('i'));
        spinner.addClass('fas fa-spinner fa-spinner-animated');
        container.css('display', 'flex');
        container.css('justify-content', 'center');
        container.css('align-items', 'center');
        container.css('width', '100%');
        container.append(spinner);
        cardGroup.append(container);
    }
    var params    = new Array();
    params['not'] = 'cards';
    resourceManager.findAllCustom(params, beforeLoad, loadDecks);
});