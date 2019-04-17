$(document).ready(function (){
    $('tr td button.ajax').click(function () {

        let radioAnswer = $(this).val();


        $('#ajax').remove();
        $('#organs').attr('id','');
        $('#hide').text('Show organs');
        $('#hide').attr('id','');



        $(this).closest('tr').attr('id','organs');
        $(this).text('Hide organs');
        $(this).attr('id','hide');

        $.ajax({
        type: "POST",
        url: "http://localhost:8080/show-organs",
        data: radioAnswer,
        dataType: "json",
        contentType:"text/plain"
    }).then(function (data, status) {

        $('#organs').after('<div id="ajax"><h3 class="text-center">All organs</h3>'
                         +'<table class="table table-hover" id="ajax-table">'
                         +'<thead>'
                         +'<tr class="row mx-auto">'
                         +'<th class="col-md-1 text-center">#</th>'
                         +'<th class="col-md-2 text-center">Name</th>'
                         +'<th class="col-md-1 text-center">Organ type</th>'
                         +'<th class="col-md-1 text-center">Shape</th>'
                         +'<th class="col-md-1 text-center">Size</th>'
                         +'<th class="col-md-2 text-center">Organ function</th>'
                         +'<th class="col-md-1 text-center">Studied by</th>'
                         +'<th class="col-md-3 text-center">Actions</th>'
                         +'</tr>'
                         +'</thead>'
                         +'<tbody id="organs-data">'
                         +'</tbody>'
                         +'</table></div>');


         for(i=0;i<data.length;i++){
             $('#organs-data').append(
             '<tr class="row mx-auto"><td class="col-md-1 text-center">' + (i+1) + '</td>'
             + '<td class="col-md-2 text-center">' + data[i].name+'</td>'
             + '<td class="col-md-1 text-center">' + data[i].organType +'</td>'
             + '<td class="col-md-1 text-center">' + (data[i].shape) +'</td>'
             + '<td class="col-md-1 text-center">' + ((data[i].size)?(data[i].size):'No') + '</td>'
             + '<td class="col-md-2 text-center">' + (data[i].organFunction) + '</td>'
             + '<td class="col-md-1 text-center">' + (data[i].studiedBy) + '</td>'
             + '<td class="col-md-2 text-center">'
                 + '<a type="button" class="btn btn-custom btn-sm"'
                    + 'href="/organs/edit/' + data[i].id +'" >Edit organ</a>'
             + '</td>'
             + '<td class="col-md-1 text-center">'
                 + '<form action="/organs/delete/' + data[i].id + '" method="post">'
                     + '<button class="btn btn-custom btn-sm">Delete</button>'
                 +'</form>'
             + '</td></tr>');
         }

       });
    });
});





