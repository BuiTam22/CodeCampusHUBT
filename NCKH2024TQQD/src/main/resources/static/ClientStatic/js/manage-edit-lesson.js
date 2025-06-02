document.getElementById('manage-score').addEventListener('click', function() {
  const slug = this.getAttribute('data-slug');
  const type = this.getAttribute('data-lesson-type');

  window.location.href = `/management/contest/score/${type}/${slug}`;
});
