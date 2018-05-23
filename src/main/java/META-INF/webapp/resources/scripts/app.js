$(document).ready(function () {

    var RALLY_DOMAIN = "http://rally-host.net/";
    var endpoint = RALLY_DOMAIN + "slm/webservice/1.40/hierarchicalrequirement.js";


    function ajaxJsonp(url, successFun) {
        $.ajax({
            url: url,
            dataType: 'jsonp',
            jsonp: 'jsonp',
            success: successFun
        });
    }

    function getStoryInfo(ref, team) {
        var storie = new StoryViewModel(team);
        var query = "?fetch=Description,Name,FormattedID,ObjectID,Actor";
        ajaxJsonp(ref + query, function (data, textStatus, jqXHR) {
            storie.code(data.HierarchicalRequirement.FormattedID);
            storie.name(data.HierarchicalRequirement.Name);
            storie.objectId(data.HierarchicalRequirement.ObjectID);
            console.log("=============================");
            console.log(JSON.stringify(data.HierarchicalRequirement.Description));
            storie.description(data.HierarchicalRequirement.Description);
            storie.actor(data.HierarchicalRequirement.Actor);
        });

        return storie;
    }

    function SprintViewModel() {
        var self = this;
        self.projectName = ko.observable("FX Strategic Renewal").extend({required: true});
        self.tranche = ko.observable("Tranche Y: Generate Electronic Confirmations - FX Cash").extend({ required: true });
        self.title = ko.observable("Sprints XX - Review").extend({ required: true });
        self.projectId = ko.observable();
        self.sprintData = ko.observable();

        self.projects = [
            {id: "19107272", name: "Payment"},
            {id: "19127775", name: "Netting"},
            {id: "2072185", name: "Core"},
            {id: "1443003", name: "Enquiry"},
            {id: "6351269", name: "CLS"},
            {id: "10779", name: "Clr Manager"},
            {id: "681264", name: "Dashboard"},
            {id: "1721056", name: "PI and"}

        ];

        self.sprints = ko.observableArray([]);
        self.stories = ko.observableArray([]);
        self.displayErrors = ko.observable(false);

        //load data to model

        self.objectToJson = function () {
            return ko.toJSON(self);
        }

        self.storyLink = function (data) {
            return RALLY_DOMAIN + "#/" + self.projectId() + "/detail/userstory/".concat(data.objectId());
        };

        self.isGenerateActive = function () {
            return  self.stories.length > 0;
        }


        self.hasNoErrors = function () {
            var errors = ko.validation.group(self.stories, {deep: true});
            var jsonErrors = JSON.parse(ko.toJSON(errors));
            var len = jsonErrors.length;

            console.debug(jsonErrors);
            if (len == 0 || (len == 1 && jsonErrors[0] == null)) {
                self.displayErrors(false);
                return true
            }
            //error, show message tpl
            self.displayErrors(true);
            return false;
        }


        self.isFieldValid = function () {
            return !self.projectName.isValid();
        }

        self.projectSelected = function () {
            self.stories([]);
            if (self.projectId() === undefined) {
                self.sprints([]);
                return false;
            }
            self.sprints([]);
            var url = RALLY_DOMAIN + "slm/webservice/1.40/project/".concat(self.projectId()) + ".js?fetch=Iterations";

            ajaxJsonp(url, function (data, textStatus, jqXHR) {
                //console.log(JSON.stringify(data));
                var results = data.Project.Iterations;
                var mappedReleases = $.map(results, function (result) {
                    console.log("RESULT=" + JSON.stringify(result));
                    var pattern = /.*iteration\/|.js/g;
                    var sprintId = result._ref.replace(pattern, "");

                    return {sprintName: result._refObjectName, sprintId: sprintId};
                });

                self.sprints(mappedReleases);
                self.sprints.sort(function (left, right) {
                    var leftVal = parseInt(left.sprintName.replace(/\D/g, ""));
                    var rigthVal = parseInt(right.sprintName.replace(/\D/g, ""));
                    return leftVal == rigthVal ? 0 : (leftVal > rigthVal ? -1 : 1)
                });
            })
        }


        self.sprintSelected = function () {

            self.displayErrors(false);
            $.get("/teams", function (teams) {
                self.teams = teams;
                var SPACE = "%20";

                var PLACEHOLDER_ID = SPACE + self.sprintData().sprintId + SPACE;

                var query = "?query=(Iteration.ObjectId%20=" + PLACEHOLDER_ID + ")";

                var FULL_URL = endpoint + query;

                console.log(FULL_URL);
                ajaxJsonp(FULL_URL, function (data, textStatus, jqXHR) {
                    var results = data.QueryResult.Results;
                    console.log("length= " + results.length);
                    var mappedStories = $.map(results, function (result) {
                        return getStoryInfo(result._ref, self.teams[0]);
                    });
                    self.stories(mappedStories);
                });
            });
        }
    }

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

    ko.validation.configure({
        insertMessages: true,
        errorMessageClass: 'help-inline'

    });

    ko.applyBindings(new SprintViewModel());
});