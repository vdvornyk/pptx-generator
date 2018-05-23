function StoryViewModel(team) {
    var self = this;
    self.objectId = ko.observable();
    self.name = ko.observable();
    self.code = ko.observable();
    self.description = ko.observable();
    self.actor = ko.observable().extend({required: true});
    self.team = ko.observable(team);
    self.feature = ko.observable(true);
}