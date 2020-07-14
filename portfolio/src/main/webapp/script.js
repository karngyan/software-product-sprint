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

// Nav buttons
const aboutButton = document.getElementById("about-btn");
const experienceButton = document.getElementById("experience-btn");
const photosButton = document.getElementById("photos-btn");

const commentTextArea = document.getElementById("comment");
const commentForm = document.getElementById("comment-form");
const commentSection = document.getElementById("comments-section");
const commentsLoader = document.getElementById("comments-loader");

const sectionIds = ["about-section", "exp-section", "photos-section"];

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

// creates the quotes component
async function createQuotesComponent() {
  const elements = document.getElementsByClassName("typewriter");

  const response = await fetch("/quotes");
  const quotes = await response.json();

  const toRotate = quotes["quotes"].map((quote) => quote.quoteList)
                                    .reduce((prevQuotes, currentQuotes) => 
                                                prevQuotes.concat(currentQuotes), []);

  for (let i = 0; i < elements.length; ++i) {
    const element = elements[i];
    const period = 2000;
    new TypewriterText(element, toRotate, period);
  }

  // cursor
  const css = document.createElement("style");
  css.type = "text/css";
  css.innerHTML = ".typewriter > .wrap { border-right: 0.08em solid #ededed}";
  document.body.appendChild(css);
};

// loads the comments section
async function loadCommentsSection() {
  const response = await fetch("/comment");
  const comments = (await response.json())["comments"];
  console.log(comments);
  const createCommentRow = ({text, createdAt}) => {
    const timeFromNow = timeSince(new Date(createdAt));

    const commentDiv = document.createElement("div");
    const commentTextDiv = document.createElement("div");
    const commentCreatedAtDiv = document.createElement("div");
    commentDiv.className = "comment";
    commentTextDiv.className = "text";
    commentCreatedAtDiv.className = "created-at";

    commentTextDiv.innerText = text;
    commentCreatedAtDiv.innerText = timeFromNow;

    commentDiv.appendChild(commentTextDiv);
    commentDiv.appendChild(commentCreatedAtDiv);

    return commentDiv;
  }
  
  if (comments.length > 0) {
    commentSection.style.display = "flex";
  }
  
  commentsLoader.style.display = "none";

  comments.forEach(comment => {
    commentSection.appendChild(createCommentRow(comment));
  })
}

function checkComment(comment) {
  comment = comment.trim();
  if (comment.length <= 0 || comment.length > 140) {
    commentTextArea.classList.add("error");
  } else {
    commentTextArea.classList.remove("error");
    commentTextArea.value = comment;
    commentForm.submit();
  }
}

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

/** 
 * Utility function to calculate time since given date 
 * I miss moment.js
 */
function timeSince(date) {
  if (typeof date !== 'object') {
    date = new Date(date);
  }

  let seconds = Math.floor((new Date() - date) / 1000);
  let intervalType;

  let interval = Math.floor(seconds / 31536000);
  if (interval >= 1) {
    intervalType = "year";
  } else {
    interval = Math.floor(seconds / 2592000);
    if (interval >= 1) {
      intervalType = "month";
    } else {
      interval = Math.floor(seconds / 86400);
      if (interval >= 1) {
        intervalType = 'day';
      } else {
        interval = Math.floor(seconds / 3600);
        if (interval >= 1) {
          intervalType = "hour";
        } else {
          interval = Math.floor(seconds / 60);
          if (interval >= 1) {
            intervalType = "min";
          } else {
            interval = seconds;
            intervalType = "sec";
          }
        }
      }
    }
  }

  if (interval > 1 || interval === 0) {
    intervalType += "s";
  }

  return interval + " " + intervalType + " ago";
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
commentForm.addEventListener('submit', (event) => {
  event.preventDefault();
  checkComment(commentTextArea.value)
});

window.onload = () => {
  createQuotesComponent();
  loadCommentsSection();
}
