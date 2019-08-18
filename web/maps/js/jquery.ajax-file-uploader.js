(function($) {

  var nextFileId = 0;

  $.fn.ajaxFileUploader = function(options) {

    console.log("upload");
    var settings = $.extend(true, {}, $.fn.ajaxFileUploader.defaults, options);

    this.filter('form').each(function() {

      var form = $(this);
      var files = form.find('input[type="file"]');
      var submitBtn = $('input[type="submit"], button[type="submit"], button:not([type])', form).first();

      files.each(function() {

        if (!this.id) {
          this.id = 'ajaxFileUploaderInput' + nextFileId++;
        }

        var browseBtn = $('<label/>').attr(settings.browseButtonAttrs);
        browseBtn.attr({
          'for': this.id,
          'title': this.title || settings.browseButtonTooltip
        });
        browseBtn.css(settings.browseButtonCss)
        browseBtn.text(settings.browseButtonText);
        browseBtn.insertAfter(this);

        var file = $(this);
        file.css({
          'display': 'block',
          'visibility': 'visible',
          'position': 'absolute',
          'width': 0,
          'height': 0,
          'overflow': 'hidden',
          'clip': 'rect(0,0,0,0)'
        });
        file.change(function() {
          if (this.value) {
            var filename = this.value.split(/\\|\//).pop();
            browseBtn.text(filename);
            browseBtn.attr('title', filename);
          } else {
            browseBtn.text(settings.browseButtonText);
            browseBtn.attr('title', this.title || settings.browseButtonTooltip);
          }
        });

      });

      submitBtn.data('loading-text', settings.submitButtonLoadingText);

      form.submit(function(e) {
        console.log("submit");
        e.preventDefault();

        var ajaxOptions = {
          url: this.action,
          method: 'POST',
          dataType: 'json',
          cache: false,
          contentType: false,
          processData: false
        };

        if (window.FormData) {
          ajaxOptions.data = new FormData(this);
        } else {    // iframeTransport
          var url = ajaxOptions.url;
          if (url.indexOf('?') != -1) {
            if (url.charAt(url.length - 1) != '?') {
              url += '&';
            }
          } else {
            url += '?';
          }
          url += 'X-Requested-With=IFrame';

          ajaxOptions.url = url;
          ajaxOptions.iframe = true;
          ajaxOptions.files = files;
          ajaxOptions.data = form.serializeArray();
        }

        submitBtn.button('loading');
        $.ajax(ajaxOptions)
          .done(function(data, textStatus, jqXHR) {
            settings.onUpload(data, textStatus, jqXHR);
          })
          .fail(function(jqXHR, textStatus, errorThrown) {
            if (settings.onError(jqXHR, textStatus, errorThrown) !== false) {
              alert(settings.uploadErrorText);
            }
          })
          .always(function() {
            submitBtn.button('reset');
            form.get(0).reset();
            files.change();
          });
      });
    });

    return this;

  };

  // Настройки плагина по-умолчанию
  $.fn.ajaxFileUploader.defaults = {
    browseButtonAttrs: {
      'class': 'btn btn-default'
    },
    browseButtonCss: {
      'width': 120,
      'overflow': 'hidden',
      '-o-text-overflow': 'ellipsis',
      'text-overflow': 'ellipsis'
    },
    browseButtonText: 'Обзор...',
    browseButtonTooltip: 'Выберите файл для загрузки',
    submitButtonLoadingText: 'Подождите...',
    uploadErrorText: 'При загрузке файла произошла ошибка',
    onError: function(jqXHR, textStatus, errorThrown) {},
    onUpload: function(data, textStatus, jqXHR) {}
  };

})(jQuery);
