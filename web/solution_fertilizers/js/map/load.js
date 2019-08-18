
window.app = window.app || {};

(function() {
  function getScript(src, callback) {
    var s = document.createElement('script');
    s.src = src;
    if (callback) s.onload = callback;
    document.body.appendChild(s);
  }

  // Файлы загружаются асинхронно, поэтому мы начинаем загружать классы-потомки
  // только после полной загрузки базовых классов
  getScript('/solution_fertilizers/js/map/polyfills.js');
  getScript('/solution_fertilizers/js/classes/BaseMapContainer.js', function () {
      //getScript('/planning_formation/js/classes/MapContainer.js');
      getScript('/solution_fertilizers/js/classes/MapContainerPFSM.js', function () {
          getScript('/solution_fertilizers/js/classes/MapMode1.js');
      });
  });
  getScript('/solution_fertilizers/js/classes/BasePolygon.js', function() {
    getScript('/solution_fertilizers/js/classes/FieldPolygon.js');
  });
  getScript('/solution_fertilizers/js/classes/FillOverlay.js');
  getScript('/solution_fertilizers/js/classes/GroundOverlay.js');
  getScript('/solution_fertilizers/js/classes/ContextMenu.js');
})();
