// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Typewriter Animation for sentences.
 */
class TypewriterText {
  /**
   * Constructor for the class
   * @param element: HTML Element - Location of the effect
   * @param toRotate: Array<String> - Quotes to rotate on the effect
   * @param period: number - time period between ticks
   */
  constructor(element, toRotate, period) {
    this.text = "";
    this.loopNumber = 0;
    this.isDeleting = false;
    this.toRotate = toRotate;
    this.element = element;
    this.period = period;
    this.tick();
  }

  /**
   * Ticks refer to typing a single letter
   */
  tick() {
    const i = this.loopNumber % this.toRotate.length;
    const fullText = this.toRotate[i];

    // reduce of increase length accordingly
    if (this.isDeleting) {
      this.text = fullText.substring(0, this.text.length - 1);
    } else {
      this.text = fullText.substring(0, this.text.length + 1);
    }

    this.element.innerHTML = '<span class="wrap">' + this.text + "</span>";

    const that = this;

    let timeoutDuration = 100 - Math.random() * 50;

    // faster deletion
    if (this.isDeleting) timeoutDuration /= 2;

    if (!this.isDeleting && this.text === fullText) {
      timeoutDuration = this.period;
      this.isDeleting = true;
    } else if (this.isDeleting && this.text === "") {
      this.isDeleting = false;
      this.loopNumber++;
      timeoutDuration = 200;
    }

    setTimeout(function () {
      that.tick();
    }, timeoutDuration);
  }
}

window.onload = () => {
  const elements = document.getElementsByClassName("typewriter");

  for (let i = 0; i < elements.length; ++i) {
    const element = elements[i];
    const toRotate = [
      "When you are backed against the wall, break the goddamn thing down.",
      "I don’t play the odds I play the man.",
      "You always have a choice.",
      "They think you care, they’ll walk all over you.",
      "Sorry, I can’t hear you over the sound of how awesome I am.",
      "Anyone can do my job, but no one can be me.",
      "I don’t have dreams, I have goals.",
      "It’s going to happen, because I am going to make it happen.",
      "It’s not bragging if it’s true.",
      "Win a no win situation by rewriting the rules.",
      "Let them hate, just make sure they spell your name right.",
    ];

    const period = 2000;
    new TypewriterText(element, toRotate, period);
  }

  // cursor
  const css = document.createElement("style");
  css.type = "text/css";
  css.innerHTML = ".typewriter > .wrap { border-right: 0.08em solid #ededed}";
  document.body.appendChild(css);
};

// Nav buttons
const aboutButton = document.getElementById("about-btn");
const experienceButton = document.getElementById("experience-btn");
const photosButton = document.getElementById("photos-btn");
const sectionIds = ["about-section", "exp-section", "photos-section"];

function showSection(button, id) {
  defocusAllButtons();
  button.className = "nav-button focus";
  sectionIds.forEach((sectionId) => {
    const el = document.getElementById(sectionId);
    el.style.display = (sectionId === id) ? 'flex' : 'none';
  })
}

function defocusAllButtons() {
  aboutButton.className = "nav-button";
  experienceButton.className = "nav-button";
  photosButton.className = "nav-button";
}

// Event Listeners
aboutButton.addEventListener('click', () => {
  showSection(aboutButton, 'about-section');
});
experienceButton.addEventListener('click', () =>  {
  showSection(experienceButton, 'exp-section')
});
photosButton.addEventListener('click', () => {
  showSection(photosButton, 'photos-section')
});
