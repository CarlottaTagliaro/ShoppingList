/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('.scendi').click(function () {
    if ($(this).hasClass('glyphicon-chevron-down')) {
        $(this).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
    }
});

//funziona randomicamente, a volte sì e altre no (se metto if..else non funziona proprio)