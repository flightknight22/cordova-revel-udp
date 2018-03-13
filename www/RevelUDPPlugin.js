
// Empty constructor
function RevelUDPPlugin() {}

// The function that passes work along to native shells
RevelUDPPlugin.prototype.send = function(port,ip,message, successCallback, errorCallback) {
    var options = {};

    options.port = port;
    options.ip = ip;
    options.message = message;
    cordova.exec(successCallback, errorCallback, 'RevelUDPPlugin', 'send', [options]);
}

// Installation constructor that binds ToastyPlugin to window
RevelUDPPlugin.install = function() {
    if (!window.plugins) {
        window.plugins = {};
    }
    window.plugins.toastyPlugin = new ToastyPlugin();
    return window.plugins.toastyPlugin;
};
cordova.addConstructor(ToastyPlugin.install);