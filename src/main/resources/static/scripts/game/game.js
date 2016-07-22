/* global $, sfx, AutoclickAgent */
(function(){

	function sfx (sound) {
  let type = sound.replace(/.*\.([^\.]+)$/, '$1')
  let audio_element = document.createElement('audio')
  if (audio_element.canPlayType(audiotypes[type])) {
    for (var i = 0; i < arguments.length; i++) {
      var source_element = document.createElement('source')
      source_element.setAttribute('src', sound)
      source_element.setAttribute('type', audiotypes[type])
      audio_element.appendChild(source_element)
    }
    audio_element.load()
    audio_element.playclip = function () {
      audio_element.pause()
      audio_element.currentTime = 0
      audio_element.play()
    }
    return audio_element
  }
}

function increaseAutoclickCost () {
	  Vars.autoclick.cost += (Vars.modifier.cost / 100)
	  updateButtonCost()
	  updateDisplayVariables()
	}

function updateButtonCost () {
	console.log('updating costs')
	  if (Vars.points >= Vars.modifier.cost) {
	    $('#modifier').css({'background-color': 'rgb(200,127,127)', 'cursor': 'pointer'})
	      .mousedown(() => { $('#modifier').stop().animate({boxShadow: 'rgb(30,30,30) 3px 3px 3px', top: 3}, 'fast') })
	      .mouseup(() => { $('#modifier').stop().animate({boxShadow: 'rgb(30,30,30) 10px 10px 15px', top: 0}, 'fast') })
	      .addClass('clickable')
	  } else {
	    $('#modifier').css({'background-color': 'gray', 'cursor': 'default'})
	      .removeClass('clickable')
	  }

	  if (Vars.points >= Vars.autoclick.cost) {
	    $('#autoclick').css({'background-color': 'rgb(200,127,127)', 'cursor': 'pointer'})
	      .mousedown(() => { $('#autoclick').stop().animate({boxShadow: 'rgb(30,30,30) 3px 3px 3px', top: 3}, 'fast') })
	      .mouseup(() => { $('#autoclick').stop().animate({boxShadow: 'rgb(30,30,30) 10px 10px 15px', top: 0}, 'fast') })
	      .addClass('clickable')
	  } else {
	    $('#autoclick').css({'background-color': 'gray', 'cursor': 'default'})
	      .removeClass('clickable')
	  }
	}

function updateDisplayVariables () {
	  $('#points_numeric').text(((Vars.points.toFixed(3) * 1000) / 1000).toString())
	  $('#per_click_text').text(Vars.modifier.current * Vars.per_click)
	  $('#modifiers_cost').text(Vars.modifier.cost)
	  $('#current_modifiers').text(Vars.modifier.current)
	  $('#modifier_text').text(50)
	  $('#count_of_autoclickers').text(Vars.autoclick.agents.length)
	  $('#autoclick_cost').text(((Vars.autoclick.cost.toFixed(3) * 1000) / 1000).toString())
	  $('#autoclick_text').text(1)
	}

class AutoclickAgent {
	  constructor () {
	    this.count = 0
	    this.base_time = 1
	    let audio = sfx('resources/Goofy Yell.mp3')
    audio.volume = 0.2
    audio.playclip()
  }

  act () {
    if (!this.cancel) {
      setTimeout(() => {
        increaseAutoclickCost()
        if (this.count++ % 10 === 0) {
          sfx('resources/D3Goblin.mp3').playclip()
        }
        $('#per_click').trigger('click')
        this.act()
      }, this.get_next_trigger_timestamp())
    }
  }

  get_next_trigger_timestamp () {
    let next = Math.floor((this.base_time + ((Math.random() - Math.random()) / 10)) * 1000)
    console.log('next trigger in ' + next + 'ms')
    return next
  }
}

var Vars = {}


var audiotypes = {
		  'mp3': 'audio/mpeg',
		  'mp4': 'audio/mp4',
		  'ogg': 'audio/ogg',
		  'wav': 'audio/wav'
		}

$().ready(() => {
  console.log('game loading')
  
  document.title = 'Three Buttons game'
  
  game_init()

  Vars.audio = sfx('resources/Click Clock Wood.mp3')
  Vars.audio.loop = true
  Vars.audio.controls = true
  Vars.audio.isPlaying = false

  $('.button').click(generic_button_press)

  $('#modifier').hover(() => {
    if (Vars.points >= Vars.modifier.cost) {
      $(this).css('cursor', 'pointer')
    } else {
      $(this).css('cursor', 'default')
    }
  })
  $('#autoclick').hover(() => {
    if (Vars.points >= Vars.modifier.cost) {
      $(this).css('cursor', 'pointer')
    } else {
      $(this).css('cursor', 'default')
    }
  })

  $('#per_click').mousedown((e) => {
    if (e.which) {
      sfx('resources/Coins Thrown.mp3').playclip()

      if(Vars.points > 0) {
        if (Vars.modifier.cost / Vars.points > 100) {
          Vars.points = ((Vars.points.toFixed(3) * 1000) / 1000)
          Vars.modifier.cost = (((Vars.modifier.cost / Vars.points).toFixed(3) * 1000) / 1000)
        } else if (Vars.modifier.cost > Vars.points) {
          Vars.points /= Vars.autoclick.cost / Vars.points
          Vars.modifier.cost = 100
        }
        if (Vars.autoclick.cost / Vars.points > 100) {
          Vars.points = ((Vars.points.toFixed(3) * 1000) / 1000)
          Vars.autoclick.cost = (((Vars.autoclick.cost / Vars.points).toFixed(3) * 1000) / 1000)
        } else if (Vars.autoclick.cost > Vars.points) {
          Vars.points /= Vars.autoclick.cost / Vars.points
          Vars.autoclick.cost = 100
        }
      }
    }
    $('#per_click').stop().animate({boxShadow: '3px 3px 3px', top: 3}, 'fast')
  })
  .mouseup(() => { $('#per_click').stop().animate({boxShadow: '10px 10px 15px', top: 0}, 'fast') })
  .addClass('clickable')


function game_init () {
  Vars.modifier = { current: 1, cost: 100, increment: 0.5 }
  Vars.autoclick = { agents: [], cost: 100 }
  Vars.points = 0
  Vars.per_click = 100
  updateDisplayVariables()
}

function increaseCost (cost) {
  return Math.floor(Math.pow((0.018 * cost + 2), 3) + 100)
}

function  generic_button_press  () {
  switch (this.id) {
    case 'modifier':
      let cost = Vars.modifier.cost
      if (Vars.points >= cost) {
        console.log('modifier clicked')
        let audio = sfx('resources/Kirby Super Star SNES - Treasure!.mp3')
        audio.volime = 0.02
        audio.playclip()

        Vars.points -= cost
        Vars.modifier.current += Vars.modifier.increment
        Vars.modifier.cost = increaseCost(cost)
        updateButtonCost()
        updateDisplayVariables()
      }
      break
    case 'autoclick':
      console.log('autoclick clicked')
      if (Vars.points >= Vars.autoclick.cost) {
        let agent = new AutoclickAgent()
        agent.act()
        Vars.autoclick.agents.push(agent)
        Vars.points -= Vars.autoclick.cost
        updateButtonCost()
        updateDisplayVariables()
      }
      break
    case 'per_click':
      console.log('per_click clicked')
      if (!Vars.audio.isPlaying) {
        Vars.audio.id = 'track_player'
        Vars.audio.playclip()
        Vars.audio.isPlaying = true
        $(Vars.audio).css({ position: 'absolute', right: 0, bottom: 0 })
          .insertAfter('p')
      }

      Vars.points += Vars.modifier.current * Vars.per_click

      updateButtonCost()
      updateDisplayVariables()
  }
}

})


window.addEventListener('popstate', function (event) {
	console.log('cancelling agents')
	for(let a in Vars.autoclick.agents) {
		Vars.autoclick.agents[a].cancel = true
	}
});

})()
