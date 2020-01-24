$(() => {
    $.ajaxSetup({
        headers: { 'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content') }
    })
    function reset() {
        $('#email').removeClass('input-invalid');
        $('#message').removeClass('input-invalid');
        $('legend').show();
        $('span').html('');
        $(".alert").show(1000);
        
    }
    //Requête d'ajout d'une saisie en bdd
    
    $('.formSaisie').submit((e) => {
        let that = e.currentTarget;
        e.preventDefault();
        reset();
        
        $.ajax({
            method: $(that).attr('method'),
            url: $(that).attr('action'),
            data: $(that).serialize()
        })
        .done((data) => {
            $('.saisie').val('');
            $('.saisies').val('');
        })
        .fail((data) => {
            alert('erreur');
            if(data.status == 422) {
                $.each(data.responseJSON.errors, function (i, error) {
                    $('form')
                        .find('[name="' + i + '"]')
                        .addClass('input-invalid')
                        .next()
                        .append(error[0]);
                });
            }
        });
    });
    $('#formSaisieUpdate').submit((e) => {
        let that = e.currentTarget;
        e.preventDefault();
        reset();
        //$('.pure-form-message').html('');
        $.ajax({
            method: $(that).attr('method'),
            url: $(that).attr('action'),
            data: $(that).serialize(),
        })
        .done((data) => {
            //$('#formCatUpdate').hide();
            //$('#formCategorie').show();
            //$('#nom_update').val('');
            
            //Rafraichir la liste des indicateurs
            $("#liste_categorie").load(location.href + " #liste_categorie");
        })
        .fail((data) => {
            if(data.status == 422) {
                $.each(data.responseJSON.errors, function (i, error) {
                    $('form')
                        .find('[name="' + i + '"]')
                        .addClass('input-invalid')
                        .next()
                        .append(error[0]);
                });
            }
        });
    });
    $('#open').on('click', (e) => {
        reset();
        $('input').val('');
        $('textarea').val('');
    });
});