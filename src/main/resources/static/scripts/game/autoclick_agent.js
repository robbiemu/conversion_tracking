/* global $, sfx, increaseAutoclickCost */
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
