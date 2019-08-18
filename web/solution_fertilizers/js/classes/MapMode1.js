(function() {
    function MapMode1(div, options) {
        //Вызываем конструктор базового класса
        app.MapContainerPFSM.call(this, div, options);
    }

    MapMode1.prototype = Object.create(app.MapContainerPFSM.prototype);

    MapMode1.prototype.constructor = MapMode1;


    /**
     *Задание fieldId
     */
    MapMode1.prototype.setFieldIdForSelect = function(id) {
        this.clickRadio_ = false;
        $("#parameters1 select[name=field1]").val(id);
        $("#parameters1 select[name=field1]").trigger("change");
    };

    window.app.MapMode1 = MapMode1;

})();
