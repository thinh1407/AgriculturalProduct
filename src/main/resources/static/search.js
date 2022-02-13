$(document).ready(function() {
    $('#myInput').on('keyup', function(event) {
        event.preventDefault();
        /* Act on the event */
        var tukhoa = $(this).val().toLowerCase();
        $('#table-list tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(tukhoa)>-1);
        });
    });
});