var Study = function($, container, modal, deck_id){
    var cardView = null;
    var Card = function(container){
        var selectedOption     = null;
        var optionViewSelected = null;
        var optionViewCorrect  = null;
        var isAnswered         = false;
        var start              = new Date();

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
        btn.addClass('to-right')
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

        this.changeBtnToNext = function(){
            btn.addClass('btn-success');
            btn.removeClass('btn-primary');
            this.setBtnTitle('Próximo >');
        }

        var isSelectedCorrectOption = function(card, option){
            return card.optionCorrect.id === option.id;
        }

        var getImageElement = function(url){
            var image = $(document.createElement('img'));
            img.attr('src', url);
            return image;
        }
        var getAudioElement = function(url){
            var audio  = $(document.createElement('audio'));
            var source = $(document.createElement('source'));
            source.attr('type', 'audio/mpeg');
            audio.attr('src', url);
            audio.append(source);
            return audio;
        }
        var addMultimidia = function(multimidia){
            var element   = null;
            switch(multimidia.type){
                case 'audio':
                    element = getAudioElement();
                break;
                case 'image':
                    element = getImageElement();
                break;
            }
            body.append(element);
        }

        var saveStudy = function(card, timeForResolution, isRight, before, after){
            var data = {
               card,
               timeForResolution,
               isRight
            };
            $.ajax({
                url: '/studies',
                data: JSON.stringify(data),
                method: 'post',
                before: before,
                success: after
            });
        }

        var getTimeForResolution = function(){
            return Math.abs(start.getTime() - new Date().getTime());
        }

        this.loadBtnEvent = function(card){
            var self = this;
            var next = false;
            btn.click(function(){
                var isRight = null;
                if(!isAnswered){
                    if(isSelectedCorrectOption(card, selectedOption)){
                        $(optionViewSelected).addClass('alert-success');
                        $(optionViewSelected).removeClass('active');
                        isRight = true;
                    }else{
                        $(optionViewSelected).addClass('alert-danger');
                        $(optionViewCorrect).addClass('alert-success');
                        $(optionViewSelected).removeClass('active');
                        isRight = false;
                    }
                    isAnswered = true;
                    addInfo(optionViewCorrect, card.info);
                    saveStudy(card, getTimeForResolution(), isRight, null, function(){
                        self.changeBtnToNext();
                        next = true;
                    });
                }else{
                    if(next){
                        $(location).attr('href', '');
                    }
                }
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
            if(card !== null){
                card = JSON.parse(card);
                if(card.hasOwnProperty('id')){
                    cardView = new Card(container);
                    cardView.setTitle(card.question);
                    if(typeof card.multimidia !== 'undefined' && card.multimidia !== null){
                        cardView.addMultimidia(card.multimidia);
                    }
                    cardView.loadBtnEvent(card);
                    callback(card);
                }else{
                    $('.card.notification').show();
                }
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