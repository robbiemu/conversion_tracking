$(function() {
      $('.input_new_url').not('.invert_visibility').toggle()
      $('.input_new_url #cancel').click(() =>{
          $('input.input_new_url:text').val("")
          $('.input_new_url').not('.invert_visibility').slideUp()
          $('.input_new_url.invert_visibility').toggle()
        })
        $('#new_url_button').click(() => {
          	$('.input_new_url.invert_visibility').toggle()
            if( $('.input_new_url').is(':visible') ) {
                $('.input_new_url').not('.invert_visibility').slideUp()
            } else {
                $('.input_new_url').not('.invert_visibility').slideDown()
          	}
        })
        
      $('#admin_outer #tabs a').click(() => {
        event.preventDefault()
      })
      $('#tabs').tab()
})