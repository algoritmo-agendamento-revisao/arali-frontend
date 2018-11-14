var Study = function($, container, modal, deck_id){
    var cardView = null;
    var Card = function(container){
        var selectedOption     = null;
        var optionViewSelected = null;
        var optionViewCorrect  = null;
        var isAnswered         = false;

        var box    = $(document.createElement('div'));
        var body   = $(document.createElement('div'));
        var title  = $(document.createElement('h5'));
        var btn    = $(document.createElement('button'));
        var footer = $(document.createElement('div'));

        box.addClass('card')
        body.addClass('card-body');
        title.addClass('card-title');
        btn.addClass('btn');
        btn.addClass('btn-primary');
        footer.addClass('card-footer');
        box.append(body);
        body.append(title);
        box.append(footer);
        footer.append(btn);

        var addInfo = function(optionView, info){
            var icon = $(document.createElement('i'));
            icon.addClass('fas');
            icon.addClass('fa-exclamation-circle');
            icon.addClass('info-icon');
            icon.click(function(){
                modal.find('.modal-content').find('.modal-body').html(info);
                modal.modal('show');
            });
            optionView.append(icon);
        }

        var isSelectedCorrectOption = function(card, option){
            return card.optionCorrect.id === option.id;
        }

        this.loadBtnEvent = function(card){
            btn.click(function(){
                if(isSelectedCorrectOption(card, selectedOption)){
                    $(optionViewSelected).addClass('alert-success');
                    $(optionViewSelected).removeClass('active');
                }else{
                    $(optionViewSelected).addClass('alert-danger');
                    $(optionViewCorrect).addClass('alert-success');
                    $(optionViewSelected).removeClass('active');
                }
                isAnswered = true;
                addInfo(optionViewCorrect, card.info);
            });
        }

        this.setTitle  = function(value){
            title.text(value);
        }
        this.addOption = function(card, option){
            var optionView = $(document.createElement('div'));
            optionView.addClass('alert');
            optionView.addClass('alert-default');
            optionView.addClass('btn');
            optionView.addClass('option');
            optionView.text(option.value);
            body.append(optionView);

            if(isSelectedCorrectOption(card, option)){
                optionViewCorrect = optionView;
            }

            optionView.click(function(){
                if(!$(this).hasClass('active') && !isAnswered){
                    selectedOption     = option;
                    optionViewSelected = optionView;
                    $(this).parents('.card-body').find('.option').removeClass('active');
                    $(this).addClass('active');
                }
            });
        }
        this.setBtnTitle = function(value){
            btn.text(value);
        }
        this.getSelectedOption = function(){
            return selectedOption;
        }

        $(container).prepend(box);
    }

    var getCard = function(deck_id, before, after){
        $.ajax({
            url: '/study/deck/' + deck_id,
            method: 'get',
            beforeSend: before,
            success: after
        });
    }

    this.load = function(){
        var callback = null;
        getCard(deck_id, null, function(card){
            if(card != null){
                card = JSON.parse(card);
                cardView = new Card(container);
                cardView.setTitle(card.question);
                cardView.loadBtnEvent(card);
                callback(card);
            }
        });

        return {
            'then': function(func){
                callback = func;
            }
        }
    }

    this.getCardView = function(){
        return cardView;
    }
}